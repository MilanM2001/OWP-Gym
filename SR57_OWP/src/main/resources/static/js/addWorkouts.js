function fillWorkoutTypes() {
	$.get("workoutTypes/workoutTypeList", function(answer) { 
		console.log(answer)

		if (answer.status == "ok") {
			var cell = $("form").find("select[name=workoutTypeID]")
			cell.empty() 

			var workoutTypes = answer.workoutTypes
			for (var itWorkoutType in workoutTypes) { 
				cell.append( 
					'<option value="' + workoutTypes[itWorkoutType].id + '">' + 
							workoutTypes[itWorkoutType].workoutTypeName + ' ' + workoutTypes[itWorkoutType].workoutTypeDescription +
					'</option>'
				)
			}
		}
	})
}

$(document).ready(function() {
	
	fillWorkoutTypes()
	
	var workoutNameInput = $("input[name=workoutName]")
	var workoutTrainerInput = $("input[name=workoutTrainer]")
	var workoutDescriptionInput = $("input[name=workoutDescription]")
	var pictureInput = $("select[name=picture]")
	var workoutTypeIDCell = $("select[name=workoutTypeID]")
	var priceInput = $("input[name=price]")
	var workoutGroupingInput = $("select[name=workoutGrouping]")
	var workoutLevelInput = $("select[name=workoutLevel]")
	var workoutLengthInput = $("input[name=workoutLength]")
	var averageGradeInput = $("input[name=averageGrade]")
	
	function add() {
		var workoutName = workoutNameInput.val()
		var workoutTrainer = workoutTrainerInput.val()
		var workoutDescription = workoutDescriptionInput.val()
		var picture = pictureInput.find("option:selected").val()
		var workoutTypeID = workoutTypeIDCell.find("option:selected").val()
		var price = priceInput.val()
		var workoutGrouping = workoutGroupingInput.find("option:selected").val()
		var workoutLevel = workoutLevelInput.find("option:selected").val()
		var workoutLength = workoutLengthInput.val()
		var averageGrade = averageGradeInput.val()
		
		
		var params = {
			workoutName: workoutName,
			workoutTrainer: workoutTrainer,
			workoutDescription: workoutDescription,
			picture: picture,
			workoutTypeID: workoutTypeID,
			price: price,
			workoutGrouping: workoutGrouping,
			workoutLevel: workoutLevel,
			workoutLength: workoutLength,
			averageGrade: averageGrade
		}
		console.log(params)
		$.post("workouts/add",
				params,
				function(){
					window.location.href = 'workouts'
				}		
		)
		console.log("POST: " + "workouts/add")
		
		return false
	
	}
	
	$("form").submit(add)
})