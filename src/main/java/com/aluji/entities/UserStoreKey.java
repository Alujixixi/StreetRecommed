package com.aluji.entities;

/*
* 在ScheduleTaskService中作为map的key存在
*
* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStoreKey {
    private Integer userId;
    private Integer storeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStoreKey)) return false;
        UserStoreKey key = (UserStoreKey) o;
        return userId == key.userId && storeId == key.storeId;
    }

    @Override
    public int hashCode() {
        return 31 * userId + storeId;
    }
}
