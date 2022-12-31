$(document).ready(function() {
	
	var userNameInput = $("input[name=userName]")
	var passwordInput = $("input[name=password]")
	var emailInput = $("input[name=email]")
	var firstNameInput = $("input[name=firstName]")
	var lastNameInput = $("input[name=lastName]")
	var dateOfBirthInput = $("input[name=dateOfBirth]")
	var addressInput = $("input[name=address]")
	var phoneNumberInput = $("input[name=phoneNumber]")
	var userRoleSelect = $("select[name=userRole]")
	
	function add() {
		var userName = userNameInput.val()
		var password = passwordInput.val()
		var email = emailInput.val()
		var firstName = firstNameInput.val()
		var lastName = lastNameInput.val()
		var dateOfBirth = dateOfBirthInput.val()
		var address = addressInput.val()
		var phoneNumber = phoneNumberInput.val()
		var userRole = userRoleSelect.find("option:selected").val()
		
		
		var params = {
			userName: userName,
			password: password,
			email: email,
			firstName: firstName,
			lastName: lastName,
			dateOfBirth: dateOfBirth,
			address: address,
			phoneNumber: phoneNumber,
			userRole: userRole
		}
		console.log(params)
		$.post("users/registration",
				params,
				function(){
					window.location.href = 'login.html'
				}		
		)
		console.log("POST: " + "users/registration")
		
		return false
	
	}
	
	$("form").submit(add) 
	
})