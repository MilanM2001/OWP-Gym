function fillUsers() {

	$.get("users/userList", function(answer) { 
		console.log(answer)

		if (answer.status == "ok") {
			var cell = $("form").find("select[name=idUser]")
			cell.empty() 

			var users = answer.users
			for (var itUser in users) {
				cell.append( 
					'<option value="' + users[itUser].id + '">' + 
							users[itUser].userName + ' ' + users[itUser].firstName + ' ' + users[itUser].lastName +
					'</option>'
				)
			}
		}
	})
}

$(document).ready(function() {

	fillUsers()
	
	var usernameInput = $("input[name=username]")
	var idUserCell = $("select[name=idUser]")
	
	function add() {

		var username = usernameInput.val()
		var idUser = idUserCell.find("option:selected").val()
	
		var params = {
			username : username,
			idUser: idUser
		}
		console.log(params)
		$.post("wishLists/add", 
				params,
				function(){
				window.location.href = 'wishLists'
				}
		)
		console.log("POST: " + "wishLists/add")
		
		return false 
	}
	
	$("form").submit(add) 
})