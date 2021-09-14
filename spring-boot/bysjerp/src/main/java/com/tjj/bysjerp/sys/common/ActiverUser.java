package com.tjj.bysjerp.sys.common;

import com.tjj.bysjerp.sys.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Owen on 2020/4/6 17:26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {

    private User user; //用户

    private List<String> roles; //角色

    private List<String> permissions; //权限

}
