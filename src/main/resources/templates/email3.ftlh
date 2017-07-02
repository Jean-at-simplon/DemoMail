<div>
<!--HEADER-->

    <#include "/include/entete.ftl">

        <!--START OF BODY-->

        <div style="padding: 15px 15px 30px 15px; font-family: Verdana, Arial, Helvetica, sans-serif; font-size:1em; background-color: #fff; color:#333;">
            <p>Bonjour</p>
            <p>
                L'employé(e) <span>${nom} </span>
                <span>${prenom}</span> est autorisé, de par son responsable, à bénéficier de
                <span>${typeConge}</span> du
                <span>${debutConge}</span> au
                <span>${finConge}</span>.<br>
            </p>
            <p>
                Cette demande a été enregistrée sous le numéro
                <span>${numDemande}</span>.
            </p>
            <p>
                Cordialement,
            </p>
            <p>
                &emsp; <em>L'équipe de gestion des absences</em>
            </p>
            <p style="color:red; font-size: 0.6em">
                *Ce message vous a été envoyé automatiquement, merci de ne pas répondre.
            </p>
        </div>

<!--FOOTER-->
        
        <#include "/include/footer.ftl">
</div>