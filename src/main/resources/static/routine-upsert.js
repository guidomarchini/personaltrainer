/* ******************** *
 * * global variables * *
 * ******************** */
let exercises;

fetch('/api/exercises', {
    method: 'GET'
}).then(function(response) {
    if(response.ok) {
        response.json().then(data => {
            exercises = data;
        })
    } else {
        handleErrorResponse(response);
    }
});

/* ******************** *
 * **** Utilities ***** *
 * ******************** */
/**
 * Given a select, fills its content with the available exercises.
 * @param select the select Element
 */
function fillExerciseOptions(select) {
    exercises.forEach(function(exercise){
        const option = document.createElement('option');
        $(option).data(exercise);
        option.innerText = exercise.name;
        select.appendChild(option);
    });
}

/**
 * This function is called onload.
 * It loads the exercises into the select, having an exercise selected.
 */
function fillLoadedExerciseOptions() {
    $('select.block-exercise').each(function() {
        const selectedValue = $(this).find('option').val();
        $(this).find('option').remove();
        fillExerciseOptions(this);
        $(this).find(`option:contains('${selectedValue}')`).prop('selected', true);
    });
}

/**
 * Deleting or moving a block should rename all blocks.
 */
function renameBlocks() {
    $('#routine-blocks')
        .children('.block-container')
        .children('.block-name')
        .each(function(index, nameContainer) {
            nameContainer.innerText = `Serie #${index}`;
        });
}

/* *********************************** *
 * ***** Add/remove blocks logic ***** *
 * *********************************** */


/* ************************ *
 * ***** Named Blocks ***** *
 * ************************ */
/**
 * Adds a named block to the routine.
 * Named blocks can't be modified.
 */
function addNamedBlock() {
    const namedBlock = $('#named-blocks-select :selected').data();
    const routineBlock = addExerciseBlock();

    $(routineBlock).find('.block-notes').text(namedBlock.notes);

    const exercisesTable = $(routineBlock).find('.block-exercises');
    namedBlock.exercises.forEach(function(exerciseRepetition){
        const newRow = $(exerciseRow());
        exercisesTable.append(newRow);

        newRow.find(`.block-exercise option:contains('${exerciseRepetition.exercise.name}')`).prop('selected', true);
        newRow.find('.block-quantity').val(exerciseRepetition.quantity);
    });
}

/* ******************************* *
 * ***** This routine blocks ***** *
 * ******************************* */
/**
 * Adds a new exercise block to the routine.
 * Returns the newly added block container.
 */
function addExerciseBlock() {
    const routineBlocks = document.getElementById('routine-blocks');

    const blockContainer = document.createElement('div');
    blockContainer.className = 'container-fluid block-container';

    const collapsibleContent = document.createElement('div');
    collapsibleContent.className = 'pb-5 collapse show';
    collapsibleContent.appendChild(blockHeader());
    let exerciseBlockContainer = blockContent();
    collapsibleContent.appendChild(exerciseBlockContainer);
    collapsibleContent.appendChild(blockButtons(blockContainer, exerciseBlockContainer));

    const triggerButton = document.createElement('a');
    triggerButton.href = "#";
    triggerButton.className = 'btn btn-dark block-name btn-block';
    triggerButton.innerText = `Serie #${routineBlocks.childElementCount}`;
    triggerButton.onclick = function() {
        $(collapsibleContent).collapse('toggle');
    };

    blockContainer.appendChild(triggerButton);
    blockContainer.appendChild(collapsibleContent);

    routineBlocks.appendChild(blockContainer);

    return blockContainer;
}

/**
 * Generates the exercise block headers (notes)
 */
function blockHeader() {
    const container = document.createElement('div');

    const label = document.createElement('label');
    label.innerText = 'Notas:';
    container.appendChild(label);

    const text = document.createElement('textarea');
    text.className = 'form-control block-notes';
    container.appendChild(text);

    return container;
}

/**
 * Generates the exercise block buttons
 */
function blockButtons(blockContainer, exerciseBlockContainer) {
    const buttonContainer = document.createElement('div');
    buttonContainer.className = 'float-right';

    const addExerciseButton = document.createElement('button');
    addExerciseButton.className = 'btn btn-dark btn-sm';
    addExerciseButton.innerText = 'Agregar ejercicio';
    addExerciseButton.onclick = function() {
        $(exerciseBlockContainer).find('tbody').append(exerciseRow());
    };

    const deleteBlockButton = document.createElement('button');
    deleteBlockButton.className = 'btn btn-secondary btn-sm';
    deleteBlockButton.innerText = 'Eliminar serie';
    deleteBlockButton.onclick = function() {
        $(blockContainer).remove();
        renameBlocks();
    };

    buttonContainer.appendChild(deleteBlockButton);
    buttonContainer.appendChild(addExerciseButton);

    return buttonContainer;
}

function addExercise(tbodyId) {
    $(`#${tbodyId}`).append(exerciseRow());
}

/**
 * Generates the block content (exercise repetitions)
 */
function blockContent() {
    const table = document.createElement('table');
    table.className = 'table table-stripped';

    const header = document.createElement('thead');
    table.appendChild(header);
    const headerRow = document.createElement('tr');
    header.appendChild(headerRow);
    headerRow.appendChild(tableColumn('Ejercicio'));
    headerRow.appendChild(tableColumn('Repeticiones'));
    headerRow.appendChild(tableColumn(''));

    const body = document.createElement('tbody');
    body.className = 'block-exercises';
    table.appendChild(body);

    return table;
}

/**
 * Generates an exercise repetition table column
 */
function tableColumn(innerText) {
    const th = document.createElement('th');
    th.scope = 'col';
    th.innerText = innerText;

    return th;
}

/**
 * Generates a row for a new exercise
 */
function exerciseRow() {
    const tr = document.createElement('tr');

    const select = document.createElement('select');
    select.className = 'block-exercise';
    fillExerciseOptions(select);

    const exerciseTh = document.createElement('th');
    exerciseTh.scope = 'col';
    exerciseTh.appendChild(select);
    tr.appendChild(exerciseTh);

    const quantityTh = document.createElement('th');
    quantityTh.scope = 'col';
    const quantityInput = document.createElement('input');
    quantityInput.className = 'block-quantity';
    quantityTh.appendChild(quantityInput);
    tr.appendChild(quantityTh);

    const removeTh = document.createElement('th');
    removeTh.scope = 'col';
    const removeIcon = document.createElement('i')
    removeIcon.setAttribute('class', 'material-icons md-24 delete')
    removeIcon.innerText = 'delete'
    removeTh.appendChild(removeIcon);
    tr.appendChild(removeTh);

    removeIcon.onclick = function(){
        $(tr).remove();
    };

    return tr;
}


/* ******************** *
 * **** Api Calls ***** *
 * ******************** */
function createRoutine() {
    upsertRoutine(undefined, 'POST');
}

function updateRoutine(routineId) {
    upsertRoutine(routineId, 'PUT');
}

function upsertRoutine(routineId, methodType) {
    const body = extractRoutine(routineId);

    console.log(body);

    fetch('/api/routines', {
        method: methodType,
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function(response) {
        if(response.ok) {
            window.location.href = `/routines`;
        } else {
            handleErrorResponse(response)
        }
    })
}

/**
 * Extracts the routine from the html
 */
function extractRoutine(routineId) {
    return {
        'id': routineId,
        'date': $('#routine-create-date').val(),
        'shortDescription': $('#routine-create-description').val(),
        'exerciseBlocks': extractBlocks(),
        'notes': $('#routine-create-notes').val()
    }
}

/**
 * Extracts the exercise blocks from the html
 */
function extractBlocks() {
    const blocks = [];

    $('#routine-blocks .block-container').each(function() {
        blocks.push(extractBlock(this))
    });

    return blocks;
}

/**
 * Extracts the exercise block from the desired block container
 */
function extractBlock(blockContainer) {
    if (jQuery.isEmptyObject($(blockContainer).data())) {
        return {
            'id': extractBlockId(blockContainer),
            'notes': $(blockContainer).find('.block-notes').val(),
            'exercises': extractBlockExercises(blockContainer)
        }
    } else {
        return $(blockContainer).data();
    }
}

/**
 * Extracts the block id from the block container
 */
function extractBlockId(blockContainer) {
    return $(blockContainer).has('.block-id') ? $(blockContainer).find('.block-id').val() : undefined;
}

/**
 * Extracts the exercise block's exercises from the desired block container
 */
function extractBlockExercises(blockContainer) {
    const blockExercises = [];

    $(blockContainer).find('.block-exercises tr').each(function() { // map doesn't work with json
        blockExercises.push({
            "exercise": $(this).find('.block-exercise :selected').data(),
            "quantity": $(this).find('.block-quantity').val()
        });
    });

    return blockExercises;
}