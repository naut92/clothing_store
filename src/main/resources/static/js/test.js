function runAjax(url, data, method, callback) {

    if (!method) method = 'GET';
    if (!url) return false;
    if (!data) data = [];
    if (typeof params != 'object') params = {};

    var queryMethod = method;

    var ajaxParams                  = {
        type: queryMethod,
        url: url,
        data: data,
        //dataType: 'JSON',
        beforeSend: function () {
            $('body').append(getLoader());
        },
        success: function (res) {
            $('#loading').remove();
            // $('#select-result').html(res);
            //console.log(res);
        },
        complete: function (res) {
            $('#loading').remove();

            try {
                res = JSON.parse(res.responseText);
            } catch (e) {
                res.response = {message : 'empty response', fail_reason: 'Unknown'};
            }

            if (res.code == 498) {
                saveUserType('');
                setPageAccessLevel('', 'Session timeout', true);
                return false;
            }

            // fire callback function
            if (typeof callback === 'function') {
                callback(res);
            }
        }
    };

    if(typeof params.processData !== 'undefined') {
        ajaxParams.processData      = params.processData;
    }

    if(typeof params.contentType !== 'undefined') {
        ajaxParams.contentType      = params.contentType;
    }

    return $.ajax(ajaxParams);
}