/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kavin.live.servicecompaintsystem;

/**
 *
 * @author SATHYA COMPUTERS
 */
import java.util.UUID;

public class UniqueIdGenerator {
    public static String generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replaceAll("-", "").substring(0, 8);
        return uniqueId;
    }
}


