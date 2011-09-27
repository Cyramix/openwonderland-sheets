package org.jdesktop.wonderland.modules.isocial.tokensheet.common;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.jdesktop.wonderland.modules.isocial.common.model.annotation.ISocialModel;

/**
 *
 * @author Kaustubh
 */
@ISocialModel
@XmlRootElement(name = "Student")
public class Student implements Serializable {

    private String name;
    private int tokenValue, passValue, strikesValues;

    public Student() {
    }

    public Student(String username) {
        this.name = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassesValue() {
        return passValue;
    }

    public void setPassesValue(int passes) {
        this.passValue = passes;
    }

    public int getStrikesValue() {
        return strikesValues;
    }

    public void setStrikesValue(int strikes) {
        this.strikesValues = strikes;
    }

    public int getTokensValue() {
        return tokenValue;
    }

    public void setTokensValue(int tokens) {
        this.tokenValue = tokens;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Tokens: " + tokenValue + ", Passes: " + passValue + ", Strikes: " + strikesValues;
    }
}
