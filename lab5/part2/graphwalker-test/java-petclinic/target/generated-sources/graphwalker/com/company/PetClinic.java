// Generated by GraphWalker (http://www.graphwalker.org)
package com.company;

import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;

@Model(file = "com/company/PetClinic.json")
public interface PetClinic {

    @Vertex()
    void v_FindOwners();

    @Edge()
    void e_HomePage();

    @Edge()
    void e_StartBrowser();

    @Edge()
    void e_Veterinarians();

    @Vertex()
    void v_Veterinarians();

    @Edge()
    void e_FindOwners();

    @Vertex()
    void v_HomePage();
}
