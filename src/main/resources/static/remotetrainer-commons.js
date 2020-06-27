function onError(response) {
    response.json().then(data => {
        $('.modal').modal('hide');
        toast(data.message);
    })
}

function toast(errorMessage) {
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