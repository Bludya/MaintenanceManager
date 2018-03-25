package org.softuni.maintenancemanager.auth.model.dtos.view;

public class AuthorityViewModel {

    private String id;

    private String authorityName;

    public AuthorityViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
