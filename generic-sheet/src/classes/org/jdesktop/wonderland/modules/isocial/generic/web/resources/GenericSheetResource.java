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

package org.jdesktop.wonderland.modules.isocial.generic.web.resources;

import com.sun.jersey.api.view.Viewable;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.common.model.SheetDetails;
import org.jdesktop.wonderland.modules.isocial.generic.common.GenericSheet;
import org.jdesktop.wonderland.modules.isocial.weblib.resources.ISocialResourceBase;

/**
 * This class stores the generic sheet resources from the web configuration page
 *
 * @author Ryan
 */
@Path("/")
public class GenericSheetResource extends ISocialResourceBase {

    @Context HttpServletRequest hsr;
    
    private static final Logger logger = Logger.getLogger(GenericSheetResource.class.getName());

    @GET
    @Path("/huh")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        
        return "Just Testing!";
    }

    @POST
    @Path("/test")
    @Consumes({"application/xml", "application/json"})
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTestMessage() {
        return "Still testing! :)";
    }

    @GET
    @Produces("application/json")
    public Response editSheet() {
        String action = hsr.getParameter("action");
        System.out.println("Action: " +action);

        dao().getSheet(hsr.getParameter("unitId"),
                       hsr.getParameter("lessonId"),
                       hsr.getParameter("sheetId"));

        return Response.ok(new Viewable("/edit", null)).cacheControl(NO_CACHE).build();

    }

    @GET
    @Path("{unitId}/{lessonId}/{sheetId}")
    @Produces({"application/xml", "application/json"})
    public Response getSheet(@PathParam("unitId") String unitId,
                             @PathParam("lessonId") String lessonId,
                             @PathParam("sheetId") String sheetId)
    {
        Sheet res = dao().getSheet(unitId, lessonId, sheetId);
        return Response.ok(res.getDetails()).cacheControl(NO_CACHE).build();
    }

    @POST
    @Path("{unitId}/{lessonId}/{sheetId}")
    @Consumes({"application/json"})
    public Response setSheetState(@PathParam("unitId") String unitId,
                                  @PathParam("lessonId") String lessonId,
                                  @PathParam("sheetId") String sheetId,
                                  Sheet sheet) {

        logger.warning("Handling POST method to publish/unplublish the sheet!");

        dao().updateSheet(sheet);
        return Response.ok().cacheControl(NO_CACHE).build();
    }

    @POST
    @Path("/update")
    @Consumes("application/json")
    public Response updateSheet(SheetDetailsWrapper wrapper) {
        logger.warning("processing sheet: "+wrapper.toString());
        
        Sheet sheet = getSheet(wrapper);
        if(sheet != null) {
            if(sheet.getDetails() instanceof GenericSheet) {

                ((GenericSheet)sheet.getDetails()).getQuestions().addAll(wrapper.getQuestions());
                ((GenericSheet)sheet.getDetails()).setQuestions(wrapper.getQuestions());
                ((GenericSheet)sheet.getDetails()).setName(wrapper.getSheetTitle());
                ((GenericSheet)sheet.getDetails()).setDockable(wrapper.getDockable());
                ((GenericSheet)sheet.getDetails()).setAutoOpen(true);
                logger.warning("Adding sheet to database. Fingers crossed!");
                
                //sheet.setPublished(wrapper.getPublish());
                dao().updateSheet(sheet);

            }
        }
        
        return Response.ok().cacheControl(NO_CACHE).build();
    }

    public void updateAndStoreSheet(SheetDetailsWrapper wrapper, boolean publish) {
        logger.warning("processing sheet: " + wrapper.toString());

        Sheet sheet = getSheet(wrapper);
        if (sheet != null) {
            if (sheet.getDetails() instanceof GenericSheet) {

                ((GenericSheet) sheet.getDetails()).getQuestions().addAll(wrapper.getQuestions());
                ((GenericSheet) sheet.getDetails()).setQuestions(wrapper.getQuestions());
                ((GenericSheet) sheet.getDetails()).setName(wrapper.getSheetTitle());
                ((GenericSheet) sheet.getDetails()).setDockable(wrapper.getDockable());
                ((GenericSheet) sheet.getDetails()).setAutoOpen(wrapper.isAutoOpen());
                ((GenericSheet) sheet.getDetails()).setDirections(wrapper.getDirections());
                ((GenericSheet) sheet.getDetails()).setSingleton(wrapper.isSingleton());
                logger.warning("Adding sheet to database. Fingers crossed!");

                sheet.setPublished(publish);
                dao().updateSheet(sheet);
                                
            }
        }
    }
    @POST
    @Path("/save")
    @Consumes("application/json")
    public Response saveSheet(SheetDetailsWrapper wrapper) {
        updateAndStoreSheet(wrapper, false);
        return Response.ok().build();
    }



    @POST
    @Path("/publish")
    @Consumes("application/json")
    public Response publishSheet(SheetDetailsWrapper wrapper) {
        updateAndStoreSheet(wrapper, true);
        return Response.ok().build();
    }


    @GET
    @Path("/cancel")
    public Response cancel() {

        return Response.ok().build();
    }

    @POST
    @Path("/TestAnswers")
    @Consumes({"application/xml", "application/json"})
    public Response TestAnswers(TestWrapperTwo answers) {

        //System.out.print(answers);
        return Response.ok().cacheControl(NO_CACHE).build();
    }
    @POST
    @Path("/testing")
    @Consumes({"application/xml", "application/json"})
    public Response testing(AbstractJSONTest sjt) {
        return Response.ok().cacheControl(NO_CACHE).build();
    }

//    @GET
//    @Path("/Sample")
//    //@Produces("application/xml")
//    @Produces("application/json")
//    public Response sampleResponse() {
//
//        SheetDetailsWrapper wrapper = null;
//        wrapper = new SheetDetailsWrapper();
//        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion();
//        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion();
//        GenericAnswer a11 = new GenericAnswer();
//        GenericAnswer a12 = new GenericAnswer();
//        GenericAnswer a21 = new GenericAnswer();
//        GenericAnswer a22 = new GenericAnswer();
//
//        a11.setQuestionTitle("What is your name?");
//        a11.setValue("amy");
//        a12.setQuestionTitle("What is your name?");
//        a12.setValue("ryan");
//
//        a21.setQuestionTitle("colors?");
//        a21.setValue("green");
//        a22.setQuestionTitle("colors?");
//        a22.setValue("blue");
//
//        question1.setInclusive(true);
//        //question1.setTitle("MultipleChoiceQuestion");
//        question1.setValue("What is your name?");
//        question1.getAnswers().add(a11);
//        question1.getAnswers().add(a12);
//
//        question2.setInclusive(true);
//        //question2.setTitle("MultipleChoiceQuestion");
//        question2.setValue("colors?");
//        question2.getAnswers().add(a21);
//        question2.getAnswers().add(a22);
//
//        YesNoQuestion question3 = new YesNoQuestion();
//        question3.setTitle("YesNoQuestion");
//        question3.setValue("A yes or no question");
//
//
//        wrapper.getQuestions().add(question1);
//        wrapper.getQuestions().add(question2);
//        wrapper.getQuestions().add(question3);
//        wrapper.setLessonId("efaf34");
//        wrapper.setUnitId("f7bf7c");
//        wrapper.setSheetId("2d50ba");
//        wrapper.setSheetTitle("sample title");
//
//        return Response.ok(wrapper).cacheControl(NO_CACHE).build();
//    }

    private Sheet getSheet(SheetDetailsWrapper wrapper) {
        Sheet s = dao().getSheet(wrapper.getUnitId(),
                                 wrapper.getLessonId(),
                                 wrapper.getSheetId());

        if(s != null)
            return s;


        logger.severe("Sheet is null in getSheet()!");
        return null;
    }
    @POST
    @Path("/SheetDetailsTest")
    @Consumes("application/json")
    @Produces("application/json")
    public Response testSheetDetails(SheetDetailsWrapper wrapper) {

           //logger.warning("test questions: "+wrapper.toString());
        return Response.ok(wrapper).cacheControl(NO_CACHE).build();
    }
}