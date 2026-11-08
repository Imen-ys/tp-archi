package dz.usdb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {

    private final List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User(1L, "Abc", "abc@usdb.dz"));
        users.add(new User(2L, "Def", "def@usdb.dz"));
        users.add(new User(3L, "Ghi", "ghi@usdb.dz"));
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
        return "redirect:/";
    }

    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        long newId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0L) + 1;
        user.setId(newId);
        users.add(user);
        return "redirect:/";
    }
}