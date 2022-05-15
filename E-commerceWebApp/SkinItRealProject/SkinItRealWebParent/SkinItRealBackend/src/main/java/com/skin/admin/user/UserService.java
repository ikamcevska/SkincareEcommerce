package com.skin.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skin.common.entity.Role;
import com.skin.common.entity.User;

@Service
public class UserService {
	public static final int USERS_PER_PAGE=4;
	@Autowired
	private UserRepository repo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passEncoder;
	
	public User getByEmail(String email) {
		return repo.getUserByEmail(email);
	}

	
	public List<User> listAllUsers(){
		return (List<User>) repo.findAll();
	}
	public Page<User> listByPage(int pageNum,String sortField,String sortDir){
		Sort sort=Sort.by(sortField);
		sort=sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable=PageRequest.of(pageNum-1,USERS_PER_PAGE,sort);
		return repo.findAll(pageable);
	}
	
	public List<Role> listRoles(){
		return (List<Role>)roleRepo.findAll();
	}
    public void save(User user) {
    	boolean isUpdatingUser=(user.getId()!=null);
    	if(isUpdatingUser){
    		User existingUser=repo.findById(user.getId()).get();
    	if(user.getPassword().isEmpty()){
    		user.setPassword(existingUser.getPassword());
    	}else{
    		encodePassword(user);
    	}
    	}else{
    		encodePassword(user);
    	}
    	repo.save(user);
    	}
    private void encodePassword(User user) {
    	String encodedPassword=passEncoder.encode(user.getPassword());
    	user.setPassword(encodedPassword);
    }
	public User get(Integer id) throws UserNotFoundException {
		try {
		return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Cound not find any user");
		}
	}
	public void delete(Integer id) throws UserNotFoundException {
		Long countById=repo.countById(id);
		if(countById==null || countById==0) {
			throw new UserNotFoundException("Cound not find any user");
		}
		repo.deleteById(id);
	}

	public boolean isEmailUnique(String email) {
		User user=repo.getUserByEmail(email);
		return user==null;
	}
	public User updateAccount(User userInForm){
		User userInDB=repo.findById(userInForm.getId()).get();

		if(!userInForm.getPassword().isEmpty()){
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		return repo.save(userInDB);
		}

}
