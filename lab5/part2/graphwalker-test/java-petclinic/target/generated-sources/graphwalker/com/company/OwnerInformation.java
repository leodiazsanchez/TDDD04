// Generated by GraphWalker (http://www.graphwalker.org)
package com.company;

import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;

@Model(file = "com/company/PetClinic.json")
public interface OwnerInformation {

    @Vertex()
    void v_OwnerInformation();

    @Edge()
    void e_UpdatePet();

    @Vertex()
    void v_FindOwners();

    @Edge()
    void e_EditPet();

    @Edge()
    void e_AddNewPet();

    @Edge()
    void e_AddVisit();

    @Edge()
    void e_FindOwners();

    @Edge()
    void e_AddPetSuccessfully();

    @Vertex()
    void v_NewPet();

    @Edge()
    void e_VisitAddedSuccessfully();

    @Vertex()
    void v_NewVisit();

    @Vertex()
    void v_Pet();

    @Edge()
    void e_AddPetFailed();

    @Edge()
    void e_VisitAddedFailed();
}
