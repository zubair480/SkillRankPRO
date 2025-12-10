package com.resumerank;

import java.util.List;

public class JobRole {
    private String roleName;
    private List<String> skills;
    private List<String> keywords;

    public JobRole(String roleName, List<String> skills, List<String> keywords) {
        this.roleName = roleName;
        this.skills = skills;
        this.keywords = keywords;
    }

    public String getRoleName() { return roleName; }
    public List<String> getSkills() { return skills; }
    public List<String> getKeywords() { return keywords; }
}
