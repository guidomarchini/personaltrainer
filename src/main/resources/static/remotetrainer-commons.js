function handleErrorResponse(response) {
    response.json().then(data => {
        onError(data.message);
    });
}

function onError(message) {
    $('#toast-type').text('Error');
    toast(message);
}

function onSuccess(message) {
    $('#toast-type').text('Exito');
    toast(message);
}

function toast(errorMessage) {
    $('.modal').modal('hide');
    $('#error-msg').text(errorMessage);
    $('#error-toast').toast('show');
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

/**
 * Removes the element for the given id
 * @param elementId
 */
function remove(elementId) {
    $(`#${elementId}`).remove();
}