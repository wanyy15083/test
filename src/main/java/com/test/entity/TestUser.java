package com.test.entity;

public class TestUser {
    private Integer id;

    private String name;

    private String address;

    private Integer age;

    private String telephone;

    public TestUser() {
    }

    public TestUser(Integer id, String telephone, Integer age, String address, String name) {
        this.id = id;
        this.telephone = telephone;
        this.age = age;
        this.address = address;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    @Override
    public String toString() {
//        return "TestUser{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", address='" + address + '\'' +
//                ", age=" + age +
//                ", telephone='" + telephone + '\'' +
//                '}';
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(id);
        sb.append(",name:").append(name);
        sb.append(",address:").append(address);
        sb.append(",age:").append(age);
        sb.append(",telephone:").append(telephone);
        return sb.toString();
    }
}