package tech.whitebox.sfa.utilities;

public enum Profile {

    BASIC_USER("CAMA.BASIC_USER_PROFILE"),
    DBSR_USER("CAMA.DBSR_USER_PROFILE"),
    MANAGER("CAMA.MANAGER_PROFILE"),
    SUPERADMIN("CAMA.SUPERADMIN_PROFILE");

    private String name;

    private Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Profile getProfileByValue(String value) {
        for (Profile p : Profile.values()) {
            if (p.getName().equals(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid value for user profiles: " + value);
    }

}
