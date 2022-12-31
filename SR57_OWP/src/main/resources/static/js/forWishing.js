function json(response) {
  return response.json()
}

fetch('workouts/wished', {
    method: 'get'
  }).then(response => {
	  return response.text()
  }).then((data) => {
	  console.log(data ? JSON.parse(data) : {})
	  workoutList = JSON.parse(data)
	  
	  var table = document.getElementById("wished");
	  for (var i=0; i < workoutList.length ; i++){
	       var tr = document.createElement('TR');
	       table.appendChild(tr);
	       
	       var tdName = document.createElement('TD');
	       tdName.appendChild(document.createTextNode(workoutList[i].workoutName));
	       tr.appendChild(tdName);
	       
	       var tdPrice = document.createElement('TD');
	       tdPrice.appendChild(document.createTextNode(workoutList[i].price));
	       tr.appendChild(tdPrice);
	       
	       var tdGrouping = document.createElement('TD');
	       tdGrouping.appendChild(document.createTextNode(workoutList[i].workoutGrouping));
	       tr.appendChild(tdGrouping);
	       
	       var tdLevel = document.createElement('TD');
	       tdLevel.appendChild(document.createTextNode(workoutList[i].workoutLevel));
	       tr.appendChild(tdLevel);
	       
	       var tdForma = document.createElement('TD')
	       var form = document.createElement('FORM')
	       
	       form.setAttribute('method', "post")
	       form.setAttribute('action', "workouts/wished/remove")
	       
	       var inputHidden = document.createElement('INPUT')
	       
	       inputHidden.setAttribute('type', "hidden")
	       inputHidden.setAttribute('name', "idWorkout")
	       inputHidden.setAttribute('value', workoutList[i].id)
	       
	       var inputSubmit = document.createElement('INPUT')
	       inputSubmit.setAttribute('type', "submit")
	       inputSubmit.setAttribute('value', "Remove from Wished")
	       
	       form.appendChild(inputHidden)
	       form.appendChild(inputSubmit)
	       
	       tdForma.appendChild(form)
	       
	       tr.appendChild(tdForma);
	    }
	})
  .catch(function (error) {
    console.log('Request failed', error);
  });