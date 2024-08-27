package com.users.outdto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressResponseTest {

  private AddressResponse addressResponse;

  @BeforeEach
  void setUp() {
    addressResponse = new AddressResponse();
    addressResponse.setId(1);
    addressResponse.setStreet("Plot No 147, Sector 74");
    addressResponse.setCity("Indore");
    addressResponse.setState("MP");
    addressResponse.setPincode(123456);
    addressResponse.setUserId(1);
  }

  @Test
  void testGettersAndSetters() {
    assertEquals(1, addressResponse.getId());
    assertEquals("Plot No 147, Sector 74", addressResponse.getStreet());
    assertEquals("Indore", addressResponse.getCity());
    assertEquals("MP", addressResponse.getState());
    assertEquals(123456, addressResponse.getPincode());
    assertEquals(1, addressResponse.getUserId());
  }

  @Test
  void testToString() {
    String expected = "AddressResponse(id=1, street=Plot No 147, Sector 74, city=Indore, state=MP, pincode=123456, userId=1)";
    assertEquals(expected, addressResponse.toString());
  }

  @Test
  void testEquals() {
    AddressResponse anotherAddressResponse = new AddressResponse();
    anotherAddressResponse.setId(1);
    anotherAddressResponse.setStreet("Plot No 147, Sector 74");
    anotherAddressResponse.setCity("Indore");
    anotherAddressResponse.setState("MP");
    anotherAddressResponse.setPincode(123456);
    anotherAddressResponse.setUserId(1);

    assertEquals(addressResponse, anotherAddressResponse);
  }

  @Test
  void testHashCode() {
    AddressResponse anotherAddressResponse = new AddressResponse();
    anotherAddressResponse.setId(1);
    anotherAddressResponse.setStreet("Plot No 147, Sector 74");
    anotherAddressResponse.setCity("Indore");
    anotherAddressResponse.setState("MP");
    anotherAddressResponse.setPincode(123456);
    anotherAddressResponse.setUserId(1);

    assertEquals(addressResponse.hashCode(), anotherAddressResponse.hashCode());
  }
}
