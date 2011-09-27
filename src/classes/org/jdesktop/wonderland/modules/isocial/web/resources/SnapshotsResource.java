/**
 * iSocial Project
 * http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as
 * subject to the "Classpath" exception as provided by the iSocial
 * project in the License file that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.web.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jdesktop.wonderland.modules.isocial.weblib.resources.ISocialResourceBase;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.darkstar.api.weblib.DarkstarRunner;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;
import org.jdesktop.wonderland.runner.RunManager;
import org.jdesktop.wonderland.runner.Runner;
import org.jdesktop.wonderland.runner.RunnerException;
import org.jdesktop.wonderland.runner.StatusWaiter;
import org.jdesktop.wonderland.web.wfs.WFSManager;
import org.jdesktop.wonderland.web.wfs.WFSSnapshot;

/**
 * Resource for managing snapshots as part of lessons
 * @author Jonathan Kaplan <jonathankap@wonderbuilders.com>
 */
@Path("/snapshots")
public class SnapshotsResource extends ISocialResourceBase {
    private static final Logger LOGGER =
            Logger.getLogger(SnapshotsResource.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getSnapshots() {
        List<WFSSnapshot> wfs = WFSManager.getWFSManager().getWFSSnapshots();
        SnapshotList out = new SnapshotList();
        
        for (WFSSnapshot snapshot : wfs) {
            String id = snapshot.getRootPath();
            String name = snapshot.getDescription();
            if (name == null || name.trim().length() == 0) {
                name = snapshot.getName();
            }
            
            out.getSnapshots().add(new Snapshot(id, name));
        }
        
        return Response.ok(out).cacheControl(NO_CACHE).build();
    }

    @POST
    public Response makeCurrent(@QueryParam("snapshotId") String id) {
        if (id == null || id.trim().length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        RunManager rm = RunManager.getInstance();

        // find the first valid Darkstar runner
        Collection<DarkstarRunner> runners = rm.getAll(DarkstarRunner.class);
        if (runners == null || runners.isEmpty()) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).
                    entity("Server unavailable").build();
        }

        DarkstarRunner runner = runners.iterator().next();

        try {
            // stop the runner
            StatusWaiter sw = rm.stop(runner, true);
            Runner.Status s = sw.waitFor();
            if (s != Runner.Status.NOT_RUNNING) {
                throw new WebApplicationException(
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                        entity("Server failed to shut down").build());
            }

            // just to be safe
            Thread.sleep(1000);

            // set the world
            runner.setWFSName(id);
            runner.forceColdstart();

            // restart it
            sw = rm.start(runner, true);
            s = sw.waitFor();
            if (s != Runner.Status.RUNNING) {
                throw new WebApplicationException(
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                        entity("Server failed to start up").build());
            }
        } catch (RunnerException re) {
            throw new WebApplicationException(re,
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                        entity("Error restarting server").build());
        } catch (InterruptedException ie) {
            throw new WebApplicationException(ie,
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                        entity("Error restarting server").build());
        }

        return Response.ok().build();
    }

    /**
     * Get a Darkstar runner.  For now, this returns the first valid
     * runner.
     * @return the runner, or null if no Darkstar runner exists
     */
    private DarkstarRunner getRunner() {
        Collection<DarkstarRunner> runners =
                RunManager.getInstance().getAll(DarkstarRunner.class);
        if (runners.isEmpty()) {
            return null;
        }

        return runners.iterator().next();
    }

    @XmlRootElement(name="snapshot-list")
    @ISocialModel
    public static class SnapshotList {
        private final List<Snapshot> snapshots = new ArrayList<Snapshot>();

        @XmlElement
        public List<Snapshot> getSnapshots() {
            return snapshots;
        }
    }

    @XmlRootElement(name="snapshot")
    @ISocialModel
    public static class Snapshot {
        private String name;
        private String id;

        public Snapshot() {}

        public Snapshot(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

    
}
