//var animalsListURL = '/animals-rest/index/animals';
//Animals--------------------------------------------
//*for Tomcat
var animalsListURL = '/index/animals';
//listTypeOfAnimals();

// Nothing to delete in initial application state
$('#btnDeleteAnimal').hide();

//* Register listeners for animals
$('#btnSearchAnimals').click(function() {
    searchAnimals($('#searchAnimals').val());
    //console.log('customer id: ' + $('#searchAnimals').val());
    //varaible for saving customer id:
    idCustomer = $('#searchAnimals').val();
    console.log('customer id: ' + idCustomer);
    return false;
});

//* Register listeners for animalsDetails
$('#btnSearchAnimalsDetails').click(function() {
    listTypeOfAnimals();
    searchAnimalsDetails($('#searchAnimalsDetails').val());
    return false;
});

function searchAnimals(searchAnimals) {
    /*if (searchAnimals == '')
     getClothesList();
     else*/ if(Math.floor(searchAnimals) == searchAnimals && $.isNumeric(searchAnimals))
        findAnimalByCustomerId(searchAnimals);
}

function searchAnimalsDetails(searchAnimalsDetails) {
    /*if (searchAnimals == '')
     getClothesList();
     else*/ if(Math.floor(searchAnimalsDetails) == searchAnimalsDetails && $.isNumeric(searchAnimalsDetails))
        findAnimalById(searchAnimalsDetails);
}


$('#btnAddAnimals').click(function() {
    //clear all forms
    $('#searchAnimals').val('');
    $('#customerList li').empty();

    $('#animalsList').empty();
    $('#detailsAnimals li').empty();
    $('#searchAnimalsDetails').val('');

    newAnimal();
    listTypeOfAnimals();
    return false;
});

$('#btnSaveAnimal').click(function() {
    if ($('#animalId').val() == '')
        addAnimal();
    else
        updateAnimal();
    return false;
});

$('#btnDeleteAnimal').click(function() {
    $('#searchAnimals').val('');
    $('#customerList li').empty();

    $('#animalsList').empty();
    $('#detailsAnimals li').empty();
    $('#searchAnimalsDetails').val('');
    deleteAnimal();
    return false;
});

function newAnimal() {
    $('#btnDeleteAnimal').hide();
    currentAnimal = {};
    renderDetailsAnimal(currentAnimal); // Display empty form
}

function findAnimalByCustomerId(customerId){
    console.log('findAnimalByCustomerId: ' + customerId);
    $.ajax({
        type: 'GET',
        url: animalsListURL + '/' + customerId + '/myanimals',
        dataType: "json",
        success: function (data) {
            console.log('findAnimalByCustomerId success: ' + customerId);
            domHandlers();
            renderAnimalsList(data);
        }
    });
}

function findAnimalById(animalId) {
    console.log('findAimalById: ' + animalId);
    $.ajax({
        type: 'GET',
        url: animalsListURL + '/'+ animalId,
        dataType: "json",
        success: function(data){
            $('#btnDeleteAnimal').show();
            console.log('findAnimalById success: ' + data.id);
            currentAnimal = data;
            renderAnimalsListDetails(currentAnimal);
            renderDetailsAnimal(currentAnimal);
        }
    });
}

function addAnimal() {
    console.log('addAnimal');
    //add CSRF AJAX/REST protection:
    csrfGet.make();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: animalsListURL,
        //when dataType: 'json', I get parseerror in server response
        dataType: 'text',
        data: formToJSONforAnimal(),
        success: function(data, textStatus, jqXHR){
            try {
                var output = JSON.parse(data);
                alert('New animal id: '  + output);
            } catch (e) {
                alert("Output is not valid JSON: " + data);
            }
            alert('Animal created successfully');
            $('#btnDeleteAnimal').show();
            $('#animalId').val(data);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('addAnimal error: ***Incorrect data***' + textStatus);
        }
    });
}

function updateAnimal() {
    console.log('updateAnimal');
    //add CSRF AJAX/REST protection:
    csrfGet.make();
    listTypeOfAnimals();
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: animalsListURL + '/' + $('#animalId').val(),
        dataType: 'json',
        data: formToJSONforAnimal(),
        success: function(data, textStatus, jqXHR){
            alert('Animal updated successfully');
        },
        error: function(xhr, status) {
            alert(xhr.status + ' Animal not found'); }
    });
}

function deleteAnimal() {
    console.log('deleteAnimal ' + $('#animalId').val());
    csrfGet.make();
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: animalsListURL + '/' + $('#animalId').val(),
        dataType: 'json',
        success: function(data, textStatus, jqXHR){
            alert('Animal deleted successfully');
        },
        error: function(xhr, status){
            alert(xhr.status + ' animal id: ' + $('#animalId').val() + ' deleteAnimal error');
        }
    });
}

function listTypeOfAnimals() {
    console.log('all type Animal');
    $.ajax({
        type: 'GET',
        url: animalsListURL,
        dataType: "json", // data type of response
        success: function (data) {
            renderTypeAnimalsList(data);
        }
    });
}

function renderTypeAnimalsList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#typesanimalId').empty();

    $.each(list, function(index, typeAnimals) {
        $('#typesanimalId').append('<option name="'+typeAnimals.id+'">'+(++index)+' - '+typeAnimals.typeanimal+'</option>');
    });
}

function renderAnimalsListDetails(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    //all clear:
    $('#customerList li').empty();
    $('#animalsList').empty();
    $('#detailsAnimals li').empty();
    $.each(list, function (index, animals) {
        if (animals.id !== null){
            $('#detailsAnimals').append('<li> Animal Id: '+animals.id+'<br> Name: '+animals.animalname+'<br> Birthday: '+animals.dateborn+'<br> Sex: '+animals.sex+'<br> Customer Id: '+animals.customersId+'<br> Type animal: '+animals.typesanimalsId+'</li>');
        }
    });
}

function renderDetailsAnimal(animal) {
    //clear another form:
    $('#customerForm')[0].reset();
    $('#searchAnimals').val('');
    $('#searchCustomer').val('');

    $('#animalId').val(animal.id);
    $('#animalN').val(animal.animalname);
    $('#animalDB').val(animal.dateborn);
    $('#animalSex').val(animal.sex);
    $('#customerId').val(animal.customersId);
    $('#typesanimalId').val(animal.typesanimalsId);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSONforAnimal() {
    var animalId = $('#animalId').val();
    return JSON.stringify({
        "id": animalId == "" ? null : animalId,
        "animalname": $('#animalN').val(),
        "dateborn": $('#animalDB').val(),
        "sex": $('#animalSex').find('option:selected').attr('name'),
        "customersId": $('#customerId').val(),
        "typesanimalsId": $('#typesanimalId').find('option:selected').attr('name')
    });
}

function getDatetimepicker() {
    $('#datetimepicker10').datetimepicker({
        viewMode: 'years',
        format: 'MM/YYYY'
    });
};

//End Animals-----------------------------------------------------

/*
 (function($, undefined){
 $(function(){
 $(document).ready(function(){


 var jsonData    = [

 {
 id:1,
 animalname:'Jack',
 dateborn:'2017.06.25',
 sex:'male',
 typesanimalsId:'int'
 },
 {
 id:2,
 animalname:'Mary',
 dateborn:'2017.06.21',
 sex:'female',
 typesanimalsId:'int'
 }
 ];
 */

//bind handlers
function domHandlers() {
    $('.animal-id').click(function(e){
        slideToggle(e);
    });
}

function renderAnimalsList(jsonData) {
    var list = jsonData;
    //all clear:
    $('#customerList li').empty();
    $('#customerForm')[0].reset();
    $('#searchCustomer').val('');

    $('#animalsList').empty();
    $('#detailsAnimals li').empty();
    $('#animalForm')[0].reset();
    $('#searchAnimals').val('');
    $('#searchAnimalsDetails').val('');

    $.each(list, function (i, animal) {
        $('#animalsList').append('<li><a href="#!" class="animal-id"> Animal Id: '   + animal.id + '</a></li>');

        //C деталями:
        $('#animalsList').append('<li class="animal-details hidden">' +
            'Name: ' + animal.animalname + '<br>' +
            'Birthday: ' + animal.dateborn + '<br>' +
            ' Sex: ' + animal.sex + '<br>' +
            ' Type animal: ' + animal.typesanimalsId + '</li>');
    });
}


function slideToggle(e) {

    $('.animal-details ').addClass('hidden');
    $(e.target).closest('li').next().removeClass('hidden');

}


//renderAnimalsList(jsonData);

//domHandlers();
//    });
//  });
//})


//old render
/*
 function renderAnimalsList(data) {
 var list = data == null ? [] : (data instanceof Array ? data : [data]);
 $('#animalsList li').remove();
 //$('#animalsListDetails li').remove();
 //$('#animalsList li').hide();
 //$('#animalsListDetails li').hide();
 $.each(list, function (index, animals) {
 /*
 $('#animalsListDetails').hide();
 $('#animalsList').click(function () {
 $(this).next().slideToggle();
 });
 */

//$('.animals').append('<ul id="animalsList"><li><a href="#"> Animal Id: ' + animals.id + '</a></li>');
/*
 $('.animals').click(function () {
 $(this).next().slideToggle();
 }).append('<ul id="animalsList"><li><a href="#"> Animal Id: ' + animals.id + '</a><br> <ul id="animalsListDetails"><li> Name: '+ animals.animalname + '<br> Birthday: ' + animals.dateborn + '<br> Sex: ' + animals.sex + '<br> Type animal: ' + animals.typesanimalsId + '</li></ul></li></ul>');
 });
 }

 /*
 $(document).ready(function(){
 $('#animalsListDetails').hide();
 $('#animalsList').click(function () {
 $(this).next().toggle();
 });
 });
 */
