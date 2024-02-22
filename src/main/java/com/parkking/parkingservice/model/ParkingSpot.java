package com.parkking.parkingservice.model;

import com.parkking.parkingservice.dto.parkingSpot.SaveParkingSpot;
import com.parkking.parkingservice.model.user.SpotOwner;
import com.parkking.parkingservice.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "parking_spots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spot_owner_id", nullable = false)
    private SpotOwner spotOwner;

    @Column(name = "spot_type")
    private String spotType;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public ParkingSpot(SaveParkingSpot saveParkingSpot) {
        this.spotType = saveParkingSpot.getSpotType();
    }

}
