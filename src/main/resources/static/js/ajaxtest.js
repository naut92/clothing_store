var ajaxtest = '/animals-rest/index/customers/ajaxtest';
//animals-rest/index/cust/ajaxtest
$(document).ready(function() {
    $("#test").click(function(){
        $.get(ajaxtest,function(data,status){
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
});