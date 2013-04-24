$(document).ready(function ()
{
    $('.languageMenu').hover(function ()
    {
        $('ul', this).fadeIn();
    }, function ()
    {
        $('ul', this).fadeOut();
    });
});