package com.example.task.manager.task;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private TypeOfDevice typeOfDevice;

    public  DeviceDTO toDeviceDTO(){
        return DeviceDTO.builder()
                .name(this.name)
                .typeOfDevice(this.typeOfDevice)
                .build();
    }
}