function enviarEmailCriacaoConta(userEmail, username, userPhone) {
    (function () {
        // Inicializa o EmailJS com sua public key
        emailjs.init('L9bh1uQOA5857Z13j'); // ← sua PUBLIC KEY
    })();

    // Parâmetros que serão enviados para o template
    let emailContent = {
        nome: username,       // ← corresponde a {{nome}} no seu template
        telefone: userPhone,  // ← corresponde a {{telefone}}
        email: userEmail      // ← corresponde a {{email}}
    };

    // IDs do seu serviço e template (os seus!)
    let serviceID = 'service_2r1ppm9';       // ← seu SERVICE ID
    let templateID = 'template_81wtsju';     // ← seu TEMPLATE ID

    // Envia o e-mail
    emailjs.send(serviceID, templateID, emailContent)
        .then(() => {
            console.log('✅ Email de boas-vindas enviado com sucesso!');
        })
        .catch((error) => {
            console.error('❌ Erro ao enviar email:', error);
        });
}
