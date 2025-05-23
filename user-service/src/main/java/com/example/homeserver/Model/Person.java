package com.example.homeserver.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Null
    private String firstName;

    @Null
    private String lastName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Định nghĩa quan hệ Many-to-Many
    // mappedBy: Chỉ ra rằng quan hệ này được sở hữu bởi trường 'persons' trong lớp Role
    // cascade: Định nghĩa các hành động cascade (VD: nếu xóa Person, không xóa Role tương ứng)
    //          CascadeType.MERGE: khi merge Person, merge cả Role
    //          CascadeType.PERSIST: khi persist Person, persist cả Role
    //          CascadeType.REFRESH: khi refresh Person, refresh cả Role
    //          CascadeType.DETACH: khi detach Person, detach cả Role
    //          CascadeType.ALL: tất cả các hành động trên
    // fetch: EAGER (tải ngay lập tức) hoặc LAZY (tải khi cần)
    //        Với ManyToMany, LAZY là mặc định và thường được khuyến nghị để tránh N+1 problem.
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST, // Nếu bạn persist Person, các Role mới cũng sẽ được persist
//            CascadeType.MERGE // Nếu bạn merge Person, các Role thay đổi cũng sẽ được merge
//    })
//    @JoinTable(
//            name = "person_roles", // Tên bảng trung gian
//            joinColumns = @JoinColumn(name = "person_id"), // Cột khóa ngoại của Person trong bảng trung gian
//            inverseJoinColumns = @JoinColumn(name = "role_id") // Cột khóa ngoại của Role trong bảng trung gian
//    )
//    private Set<Role> roles = new HashSet<>();


    @Setter
    @Getter
    @ManyToMany(cascade = {
            CascadeType.PERSIST, // Nếu bạn persist Person, các thành viên gia đình mới cũng sẽ được persist
            CascadeType.MERGE    // Nếu bạn merge Person, các thay đổi trên thành viên gia đình cũng sẽ được merge
    })
    @JoinTable(
            name = "person_family_relationships", // Tên bảng trung gian cho mối quan hệ gia đình
            joinColumns = @JoinColumn(name = "person_id"),          // ID của người A
            inverseJoinColumns = @JoinColumn(name = "related_person_id") // ID của người B (người thân)
    )
    private Set<Person> familyMembers = new HashSet<>();


    public void addFamilyMember(Person member) {
        // Đảm bảo không thêm chính nó
        if (this.equals(member)) {
            return;
        }
        this.familyMembers.add(member);
        // Đảm bảo mối quan hệ hai chiều được thiết lập
        if (!member.getFamilyMembers().contains(this)) {
            member.addFamilyMember(this); // Gọi lại để đảm bảo đối tượng kia cũng có mối quan hệ
        }
    }

    public void removeFamilyMember(Person member) {
        this.familyMembers.remove(member);
        // Đảm bảo mối quan hệ hai chiều được xóa
        if (member.getFamilyMembers().contains(this)) {
            member.removeFamilyMember(this); // Gọi lại để đảm bảo đối tượng kia cũng xóa mối quan hệ
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id != null && Objects.equals(id, person.id); // Chỉ so sánh ID
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : 0; // Chỉ dùng ID để tính hash code
    }
}
