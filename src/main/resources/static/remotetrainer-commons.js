function onError(response) {
    response.json().then(data => {
        $('.modal').modal('hide');
        $('#error-msg').text(data.message);
        $('#error-toast').toast('show');
    })
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