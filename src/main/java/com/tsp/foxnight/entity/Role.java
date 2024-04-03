package com.tsp.foxnight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity {
    @NotBlank
    private String name;
//    @JsonIgnore
//    @OneToMany(mappedBy = RolePrivileges.Fields.roleId)
//    private List<RolePrivileges> rolePrivileges;

}
