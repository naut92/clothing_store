//var customerlistURL = '/animals-rest/index/customers';
//*for Tomcat
var clothesListURL = '/clothes';
//*/
var currentClothes;

//$.ajaxSetup({cache: false});

//add CSRF AJAX/REST protection:
//*


var csrfGet = {make: CSRF};
function CSRF() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}
//*/
/*
$(document).ajaxError(function(event, request, settings) {
	alert("Error accessing the server");
});
*/
//Clothes---------------------------------------

// Nothing to delete in initial application state
$('#btnDeleteClothes').hide();

// Register listeners for customers
$('#btnSearchClothes').click(function() {
    search($('#searchClothes').val());
    return false;
});

//инициализация idCustomer:
var idCustomer = 887;

function search(searchKey) {
    if (searchKey == '')
        getClothesList();
    else if(Math.floor(searchKey) == searchKey && $.isNumeric(searchKey))
        findCustomerById(searchKey);
    else
        findByLName(searchKey);
}

 //Trigger search when pressing 'Return' on search key input field
$('#searchClothes').keypress(function(e){
    if(e.which == 13) {
        search($('#searchClothes').val());
        e.preventDefault();
        return false;
    }
});

$('#btnAddClothes').click(function() {
    $('#searchClothes').val('');
    $('#clothesList li').empty();

    $('#searchAnimals').val('');
    $('#animalsList').empty();
    $('#detailsAnimals li').empty();
    $('#searchAnimalsDetails').val('');
    newClothes();

    return false;
});

$('#btnSaveCustomer').click(function() {
    if ($('#custId').val() == '')
        addClothes();
    else
        updateClothes();
    return false;
});

$('#btnDeleteClothes').click(function() {
    deleteClothes();
    return false;
});

function newClothes() {
    $('#btnDeleteClothes').hide();
    currentClothes = {};
    renderDetailsClothes(currentClothes); // Display empty form
}

function getClothesList() {
    console.log('all Clothes');
    $.ajax({
        type: 'GET',
        url: clothesListURL,
        dataType: "json", // data type of response
        success: function (data) {
            renderClothesList(data);
        }
    });
}

function findByLName(searchKey) {
    console.log('findByLName: ' + searchKey);
    $.ajax({
        type: 'GET',
        url: clothesListURL + '/customerLn/' + searchKey,
        dataType: "json",
        success: function (searchKey) {
            renderClothesList(searchKey);
        },
        error: function(xhr, status) {
            alert(xhr.status + 'Customer not found'); }
    });
}

function findCustomerById(id) {
    console.log('findCustomerById: ' + id);
    $.ajax({
        type: 'GET',
        url: clothesListURL + '/' + id,
        dataType: "json",
        success: function(data){
            $('#btnDeleteClothes').show();
            console.log('findCustomerById success: ' + data.id);
            currentClothes = data;
            renderDetailsClothes(currentClothes);
            renderClothesList(currentClothes);

        },
        error: function(xhr, status) {
            alert(xhr.status + ' Customer not found'); }
    });
}

function addClothes() {
    console.log('addClothes');
    //add CSRF AJAX/REST protection:
    csrfGet.make();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: clothesListURL,
        //when dataType: 'json', I get parseerror in server response
        dataType: 'text',
        data: formToJSONforClothes(),
        success: function(data, textStatus, jqXHR){
            try {
                var output = JSON.parse(data);
                alert('New clothes id: '  + output);
            } catch (e) {
                alert("Output is not valid JSON: " + data);
            }
            alert('Clothes created successfully');
            $('#btnDeleteClothes').show();
            $('#clothId').val(data);
        },
        error: function(xhr, status){
            alert(xhr.status + ' add Clothes error: Incorrect email/or exist email!');
        }
    });
}

function updateClothes() {
    console.log('updateClothes');
    //add CSRF AJAX/REST protection:
    csrfGet.make();
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: clothesListURL + '/' + $('#clothId').val(),
        dataType: 'json',
        data: formToJSONforClothes(),
        success: function(data, textStatus, jqXHR){
            alert('Clothes updated successfully');
        },
        error: function(xhr, status) {
            alert(xhr.status + ' Clothes not found'); }
    });
}

function deleteClothes() {
    console.log('deleteClothes ' + $('#clothId').val());
    csrfGet.make();
    $.ajax({
        type: 'DELETE',
        url: clothesListURL + '/' + $('#clothId').val(),
        success: function(data, textStatus, jqXHR){
            alert('Clothes deleted successfully');
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('deleteClothes error');
        }
    });
}

function renderClothesList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    //all clear:
    $('#clothesList li').empty();
    //$('#animalsList').empty();
    //$('#detailsAnimals li').empty();

    $.each(list, function(index, clothes) {
        $('#clothesList').append('<li> Id: '+clothes.id+'<br> Clothes: '+clothes.name+'<br>Size: '+clothes.size+'<br> Cost: '+clothes.cost+'<br>  Color: '+clothes.color+ '<br>Type clothes: '+clothes.type+'<br>' +
            'Description: '+clothes.description+'<br><br></li>');
//animals:
      //  $.each(clothes.animalsById, function (index, animalsById) {
       //     if (animalsById.id !== null){
         //       $('#clothesList').append('<li> Animal Id: '+animalsById.id+'<br> Name: '+animalsById.animalname+'<br> Birthday: '+animalsById.dateborn+'<br> Sex: '+animalsById.sex+'<br> Type animal: '+animalsById.typesanimalsId+'</li>');
         //   }
        //});
    });
}

function renderDetailsClothes(clothes) {
    //clear another form:
    $('#animalForm')[0].reset();
    $('#searchAnimals').val('');
    $('#searchAnimalsDetails').val('');

    $('#clothName').val(clothes.id);
    $('#clothSize').val(clothes.name);
    $('#clothColor').val(clothes.size);
    $('#clothCost').val(clothes.cost);
    $('#clothType').val(clothes.type);
    $('#clothDescription').val(clothes.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSONforClothes() {
    var clothesId = $('#clothId').val();
    return JSON.stringify({
        "id": clothesId == "" ? null : clothesId,
        "clothName": $('#clothName').val(),
        "clothSize": $('#clothSize').val(),
        "clothCost": $('#clothCost').val(),
        "clothColor": $('#clothColor').val(),
        "clothType": $('#clothType').val(),
        "clothDescription": $('#clothDescription').val()
    });
}


//End Clothes--------------------------------------