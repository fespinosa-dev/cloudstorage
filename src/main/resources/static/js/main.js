// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

function showMessage(msgId, msg) {
    $("#" + msgId).html(msg.replaceAll("^\"|\"$", ""));
    $("#" + msgId).removeClass("d-none");
}

function addNote(note) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/note/add",
        data: JSON.stringify(note),
        dataType: 'json',
        success: function (data) {
            loadNoteList();
            showMessage("noteMessage", data);
        },
        error: function (xhr, status, error) {
            showMessage("noteErrMessage", xhr.responseText);
            $('#noteModal').modal('hide');
        }
    });
}

function updateNote(note) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/note/update",
        data: JSON.stringify(note),
        dataType: 'json',
        success: function (data) {
            loadNoteList();
            showMessage("noteMessage", data);
        },
        error: function (xhr, status, error) {
            showMessage("noteErrMessage", xhr.responseText);
            $('#noteModal').modal('hide');
        }
    });
}

function saveNoteChanges(ev) {
    ev.preventDefault();
    var note = {};
    note['id'] = $('#note-id').val();
    note["title"] = $('#note-title').val();
    note["description"] = $('#note-description').val();
    if (note.id != "") {
        updateNote(note);
    } else {
        addNote(note);
    }

}

function deleteNote(noteId) {
    var note = {};
    note["id"] = noteId;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/note/delete",
        data: JSON.stringify(note),
        dataType: 'json',
        success: function (data) {
            loadNoteList();
            showMessage("noteMessage", data);
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function deleteFile(id) {
    var file = {};
    file["id"] = id;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/file/delete",
        data: JSON.stringify(file),
        dataType: 'json',
        success: function (data) {
            loadFileList();
        },
        error: function (xhr, status, error) {
            showMessage("noteErrMessage", xhr.responseText);
            $('#noteModal').modal('hide');
        }
    });
}

function loadNoteList() {
    $.ajax({
        type: 'get',
        url: '/note/list',
        success: function (notesData) {
            $('#note_list').replaceWith(notesData);
            $('#noteModal').modal('hide');
        },
        error: function (e) {
            showMessage("noteErrMessage", "There was an error loading the note list.")
        }
    })
}


function saveCredentialChanges(ev) {
    ev.preventDefault();
    var credential = {};
    credential['id'] = $('#credential-id').val();
    credential["url"] = $('#credential-url').val();
    credential["username"] = $('#credential-username').val();
    credential["password"] = $('#credential-password').val();
    if (credential.id != "") {
        updateCredential(credential);
    } else {
        addCredential(credential);
    }
}

function addCredential(credential) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/credential/add",
        data: JSON.stringify(credential),
        dataType: 'json',
        success: function (data) {
            loadCredentialList();
            showMessage("credentialMessage", data);
        },
        error: function (xhr, status, error) {
            showMessage("credentialErrMessage", xhr.responseText);
            $('#CredentialModal').modal('hide');
        }
    });
}

function updateCredential(credential) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/credential/update",
        data: JSON.stringify(credential),
        dataType: 'json',
        success: function (data) {
            loadCredentialList();
            showMessage("credentialMessage", data);
        },
        error: function (xhr, status, error) {
            showMessage("credentialErrMessage", xhr.responseText);
            $('#CredentialModal').modal('hide');
        }
    });
}

function deleteCredential(credentialId) {
    var credential = {};
    credential["id"] = credentialId;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/credential/delete",
        data: JSON.stringify(credential),
        dataType: 'json',
        success: function (data) {
            loadCredentialList();
            showMessage("credentialMessage", data);
        },
        error: function (xhr, status, error) {
            showMessage("credentialErrMessage", xhr.responseText);
            $('#CredentialModal').modal('hide');
        }
    });
}


function loadCredentialList() {
    $.ajax({
        type: 'get',
        url: '/credential/list',
        success: function (credentialList) {
            console.log(credentialList);
            $('#credential_list').replaceWith(credentialList);
            $('#credentialModal').modal('hide');
        },
        error: function (e) {
            showMessage("noteErrMessage", "There was an error loading the credential list.")
        }
    })
}

function loadFileList() {
    $.ajax({
        type: 'get',
        url: '/file/list',
        success: function (fileList) {
            console.log(fileList);
            $('#file_list').replaceWith(fileList);
        },
        error: function (e) {
            console.log(e)
        }
    })
}


