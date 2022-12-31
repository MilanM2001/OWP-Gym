function fillWorkouts() {

	$.get("workouts/workoutList", function(answer) { 
		console.log(answer)

		if (answer.status == "ok") {
			var cell = $("form").find("select[name=idWorkout]")
			cell.empty() 

			var workouts = answer.workouts
			for (var itWorkout in workouts) {
				cell.append( 
					'<option value="' + workouts[itWorkout].id + '">' + 
							workouts[itWorkout].workoutName + ' ' + workouts[itWorkout].price +
					'</option>'
				)
			}
		}
	})
}

$(document).ready(function() {

	fillWorkouts()
	
	var commentTextTextInput = $("input[name=commentText]")
	var commentGradeTextInput = $("input[name=commentGrade]")
	var dateOfCreationTextInput = $("input[name=dateOfCreation]")
	var usernameTextInput = $("input[name=username]")
	var idWorkoutCell = $("select[name=idWorkout]")
	var commentStatusSelect = $("select[name=commentStatus]")
	var anonymousTextInput = $("input[name=anonymous]")
	
	function add() {

		var commentText = commentTextTextInput.val()
		var commentGrade = commentGradeTextInput.val()
		var dateOfCreation = dateOfCreationTextInput.val()
		var username = usernameTextInput.val()
		var idWorkout = idWorkoutCell.find("option:selected").val()
		var commentStatus = commentStatusSelect.find("option:selected").val()
		var anonymous = anonymousTextInput.val()
	
		var params = {
			commentText : commentText,
			commentGrade : commentGrade,
			dateOfCreation : dateOfCreation,
			username : username,
			idWorkout: idWorkout,
			commentStatus : commentStatus,
			anonymous : anonymous
		}
		console.log(params)
		$.post("comments/add", 
				params,
				function(){
				window.location.href = 'comments'
				}
		)
		console.log("POST: " + "comments/add")
		
		return false 
	}
	
	$("form").submit(add) 
})