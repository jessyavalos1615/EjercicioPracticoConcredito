package com.example.ejerciciopracticoconcredito;

public class Prospecto {
    private String id;
    private String name;
    private String last_name;
    private String second_last_name;
    private String street;
    private String number;
    private String suburb;
    private String zip;
    private String phone;
    private String rfc;
    private String status;
    private String observacion;

    public Prospecto() {
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSecond_last_name() {
        return second_last_name;
    }

    public void setSecond_last_name(String second_last_name) {
        this.second_last_name = second_last_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "Prospecto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", second_last_name='" + second_last_name + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", suburb='" + suburb + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", rfc='" + rfc + '\'' +
                ", status='" + status + '\'' +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}
