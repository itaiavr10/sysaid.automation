package buisness.installer.steps;


public class InstallerApi {
	
	
	public InstallerWelcomeStep WelcomeStep;
	public InstallerLicenseAgreementStep LicenseAgreementStep;
	public InstallerSetupTypeStep SetupTypeStep;
	public InstallerFolderDestinationStep FolderDestinationStep;
	public InstallerRepositoryDestinationStep PatchMngRepositoryStep;
	public InstallerStartMenuFolderStep StartMenuProgramStep;
	public InstallerLicenseFileStep LicenseFileStep;
	public InstallerUserCredentialsStep UserCredentialsStep;
	public InstallerCompletedStep CompletedStep;
	public InstallerSelectDbStep SelectDbStep;
	public InstallerDbSettingStep DbSettingStep;
	public InstallerEmailSettingStep EmailSettingStep;
	public InstallerHttpPortStep HttpPortStep;
	public InstallerLdapStep LdapStep;
	public InstallerLanguageStep LanguageStep;
	
	
	public InstallerApi(){
		SetupTypeStep = new InstallerSetupTypeStep();
		FolderDestinationStep = new InstallerFolderDestinationStep();
		PatchMngRepositoryStep = new InstallerRepositoryDestinationStep();
		StartMenuProgramStep = new InstallerStartMenuFolderStep();
		LicenseFileStep = new InstallerLicenseFileStep();
		WelcomeStep= new InstallerWelcomeStep();
		LicenseAgreementStep = new InstallerLicenseAgreementStep();
		UserCredentialsStep = new InstallerUserCredentialsStep();
		CompletedStep  = new InstallerCompletedStep();
		SelectDbStep = new InstallerSelectDbStep();
		DbSettingStep = new InstallerDbSettingStep();
		EmailSettingStep = new InstallerEmailSettingStep();
		HttpPortStep = new InstallerHttpPortStep();
		LdapStep = new InstallerLdapStep();
		LanguageStep = new InstallerLanguageStep();
	}

}
