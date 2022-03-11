Page Title: Altoro.Mutual

#Object Definitions
====================================================================================

login_Link              css             #_ctl0__ctl0_Content_AccountLink
personal_Link           linktext        AMAZON
inp_username            id              uid
inp_password            id              password
btn_signin              xpath           (//span[text()='Sign in'])[2]
====================================================================================

@all
--------------------------------
login_Link:
    left-of personal_Link ~30px
personal_Link:
    right-of login_Link ~13px