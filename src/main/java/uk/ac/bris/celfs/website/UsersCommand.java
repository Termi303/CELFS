/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import lombok.Data;

/**
 *
 * @author lucas
 */

@Data
public class UsersCommand {
    String username;
    String password;
    String u_type;
}
