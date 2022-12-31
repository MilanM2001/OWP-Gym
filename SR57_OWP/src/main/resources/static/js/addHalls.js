$(document).ready(function() {
	
	var hallLabelInput = $("input[name=hallLabel]")
	var capacityInput = $("input[name=capacity]")
	
	function add() {
		var hallLabel = hallLabelInput.val()
		var capacity = capacityInput.val()
		
		var params = {
			hallLabel: hallLabel,
			capacity: capacity
		}
		console.log(params)
		$.post("halls/add",
				params,
				function(){
					window.location.href = 'halls'
				}		
		)
		console.log("POST: " + "halls/add")
		
		return false
	
	}
	
	$("form").submit(add)
})