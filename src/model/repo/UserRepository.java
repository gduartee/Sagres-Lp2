package model.repo;

import model.entity.User;

public class UserRepository extends JsonRepository<User> {
    public UserRepository() {
        super("data/users.json", User.class, User::getId, (u, id) -> u.setId(id));
    }
}
