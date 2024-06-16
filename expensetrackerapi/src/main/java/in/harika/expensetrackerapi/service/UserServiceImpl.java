package in.harika.expensetrackerapi.service;

import in.harika.expensetrackerapi.entity.User;
import in.harika.expensetrackerapi.entity.UserModel;
import in.harika.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import in.harika.expensetrackerapi.exceptions.ResourceNotFoundException;
import in.harika.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserModel user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("User is already registered with email:" + user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User read() throws ResourceNotFoundException {
        Long userId = getLoggedInUser().getId();
        return  userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User no found for the id:" + userId));
    }

    @Override
    public User update(UserModel user) throws ResourceNotFoundException {
        User oUser = read();
        oUser.setName(user.getName()!=null ? user.getName() : oUser.getName());
        oUser.setEmail(user.getEmail()!=null ? user.getEmail(): oUser.getEmail());
        oUser.setPassword(user.getPassword()!=null ? bcryptEncoder.encode(user.getPassword()): oUser.getPassword());
        oUser.setAge(user.getAge()!=null ? user.getAge() : oUser.getAge());
        return userRepository.save(oUser);
    }

    @Override
    public void delete() {
        User user = read();
        userRepository.delete(user);}

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email"+ email));

    }
}