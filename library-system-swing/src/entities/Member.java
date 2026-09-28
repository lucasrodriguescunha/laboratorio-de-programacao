package entities;

import util.PhoneUtils;

public class Member {

    private static int nextId = 1;

    private final int id;
    private String name;
    private String registration;
    private String contact;
    private boolean active = true;

    public Member(String name, String registration, String contact) {
        this.id = nextId++;
        this.name = name;
        this.registration = registration;
        this.contact = contact;
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getFormattedContact() { return PhoneUtils.format(contact); }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getStatusLabel() { return active ? "Ativo" : "Inativo"; }

    @Override
    public String toString() {
        return "#" + id + " - " + name + " (matrícula " + registration + ")";
    }
}
