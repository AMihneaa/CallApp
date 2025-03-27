package com.tpi.demo.models.Role;

import com.tpi.demo.models.Privilege.Privilege;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collation = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private Long id;


    private String name;

    @DBRef
    private Set<Privilege> privilege = new HashSet<>();

    public Role(String name, Set<Privilege> privileges){
        this.name = name;
        this.privilege = privileges;
    }
}
