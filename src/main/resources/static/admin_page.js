
$(document).ready(

    function () {
        ajaxGetAll();
        getCurrentUser();
        }
    )

//GET ALL

function ajaxGetAll() {
    $.ajax({
        type: "GET",
        url: "/admin/getUsers",
        success: function (result) {
            $("#userTable > tbody").empty();
            $.each(result,
                function (i, user) {
                    var newRows =
                        "<tr>" +
                        "<td>" + user.id + "</td>" +
                        "<td>" + user.firstName + "</td>" +
                        "<td>" + user.lastName + "</td>" +
                        "<td>" + user.age + "</td>" +
                        "<td>" + user.email + "</td>" +
                        "<td>" + user.username + "</td>" +
                        "<td>";
                    $.each(user.roles, function (i, role) {
                        newRows = newRows + "<normal> " + role.role + "</normal>"
                    })
                    newRows = newRows + "</td>" +
                        "<td><div class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"getUser(" + user.id + ")\">Update</div>" +
                        "</td>" +
                        "<td><div class=\"btn btn-danger\"  data-toggle=\"modal\" data-target=\"#myModalDelete\" onclick=\"getUserforDelete(" + user.id + ")\">Delete</div></td>" +
                        "</tr>";
                    $('#getResultTable ').append(newRows)
                });
            console.log("Success: ", result);
            getCurrentUser();
        },
        error: function (e) {
            $("#getResultDiv").html("<strong>Error</strong>");
            console.log("ERROR: ", e);
        }
    });
}


// CREATE

$("#newUserForm").submit(function (event) {
    event.preventDefault();
    ajaxPost();
    $('#create_navbar').attr("class", "nav-link");
    $('#create').attr("class", "tab-pane");
    $('#users_navbar').attr("class", "nav-link active");
    $('#users').attr("class", "tab-pane active");
})

function ajaxPost() {
    var selectedRoles = [];
    $("#roles :selected").each(function () {
        if ($(this).val() == "ROLE_ADMIN") {
            var currentRoleObject = {'id': 1, 'role': $(this).val()};
        }
        if ($(this).val() == "ROLE_USER") {
            var currentRoleObject = {'id': 2, 'role': $(this).val()};
        }
        selectedRoles.push(currentRoleObject);
    });

    var formData = {
        // id : $("#id").val(),
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        username: $("#username").val(),
        age: $("#age").val(),
        password: $("#password").val(),
        roles: selectedRoles
    }

    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "saveUser",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (result) {
                ajaxGetAll();
            console.log(result);
        },
        error: function (e) {
            alert("Error!")
            console.log("ERROR: ", e);
        }
    });

}

//DELETE
function getUserforDelete(id) {
    $.ajax({
        method: "GET",
        url: "/admin/" + id,
        success: function (result) {
            $('#modal_delete_id').attr("value", result.id);
            $('#modal_delete_id').attr("placeholder", result.id);
            $('#modal_delete_id').prop('readonly', true);
            $('#modal_delete_firstName').attr("value", result.firstName);
            $('#modal_delete_firstName').attr("placeholder", result.firstName);
            $('#modal_delete_lastName').attr("value", result.lastName);
            $('#modal_delete_lastName').attr("placeholder", result.lastName);
            $('#modal_delete_age').attr("value", result.age);
            $('#modal_delete_age').attr("placeholder", result.age);
            $('#modal_delete_email').attr("value", result.email);
            $('#modal_delete_email').attr("placeholder", result.email);
            $('#modal_delete_username').attr("value", result.username);
            $('#modal_delete_username').attr("placeholder", result.username);
            $('#modal_delete_roles_admin').text("");
            $('#modal_delete_roles_user').text("");

            $.each(result.roles, function (i, role) {
                if (role.role == "ROLE_ADMIN") {
                    // $('#modal_delete_roles_admin').attr("text","ADMIN");
                    $('#modal_delete_roles_admin').text("ADMIN");
                }
                if (role.role == "ROLE_USER") {
                    // $('#modal_delete_roles_user').attr("text","USER")
                    $('#modal_delete_roles_user').text("USER");
                }
            })
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function deleteUser() {
    $.ajax({
        url: "/admin/" + $("#modal_delete_id").val(),
        method: "DELETE",
        success: function () {
            ajaxGetAll();
        },
        error: function (error) {
            alert(error);
        }
    })
}



//get current user info
function getCurrentUser() {
    $.ajax({
        method: "GET",
        url: "/admin/getCurrentUser",
        success: function (result) {
            $('#current_username').text(result.username + "\u00A0");
            var corrent_roles = " with roles: ";
            $.each(result.roles, function (i, role) {
                corrent_roles = corrent_roles + role.role.substring(5) + " ";
            })
            $('#current_roles').text(corrent_roles);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}




//UPDATE
function getUser(id) {
    $.ajax({
        method: "GET",
        url: "/admin/" + id,
        success: function (result) {
            $('#modal_id').attr("value", result.id);
            $('#modal_id').attr("placeholder", result.id);
            $('#modal_id').prop('readonly', true);
            $('#modal_firstName').attr("value", result.firstName);
            $('#modal_firstName').attr("placeholder", result.firstName);
            $('#modal_lastName').attr("value", result.lastName);
            $('#modal_lastName').attr("placeholder", result.lastName);
            $('#modal_age').attr("value", result.age);
            $('#modal_age').attr("placeholder", result.age);
            $('#modal_email').attr("value", result.email);
            $('#modal_email').attr("placeholder", result.email);
            $('#modal_username').attr("value", result.username);
            $('#modal_username').attr("placeholder", result.username);

        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function updateUser() {

    var selectedRoles = [];
    $("#modal_roles :selected").each(function () {
        if ($(this).val() == "ROLE_ADMIN") {
            var currentRoleObject = {'id': 1, 'role': $(this).val()};
        }
        if ($(this).val() == "ROLE_USER") {
            var currentRoleObject = {'id': 2, 'role': $(this).val()};
        }
        selectedRoles.push(currentRoleObject);
    });

    var formData = {
        id: $("#modal_id").val(),
        firstName: $("#modal_firstName").val(),
        lastName: $("#modal_lastName").val(),
        username: $("#modal_username").val(),
        age: $("#modal_age").val(),
        email: $("#modal_email").val(),
        roles: selectedRoles,
        password: $("#modal_newPassword").val(),
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "updateUser",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (result) {
            $('#myModal').modal('toggle');
            ajaxGetAll();
            console.log(result);
        },
        error: function (e) {
            alert("Error!")
            console.log("ERROR: ", e);
        }
    });

}
