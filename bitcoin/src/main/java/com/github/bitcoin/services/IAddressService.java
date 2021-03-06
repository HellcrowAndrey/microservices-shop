package com.github.bitcoin.services;

import com.github.bitcoin.entity.Address;
import com.github.bitcoin.entity.EntityStatus;

import java.util.List;

public interface IAddressService {

    List<Address> createAll(List<Address> addresses);

    List<String> readAllAddresses();

    Address readByAddress(String address);

    void update(Address address);

    void updateStatus(String address, EntityStatus status);

}
