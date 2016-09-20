package buisness.installer.steps;


public class InstallerApi {
	
	
	public InstallerSetupTypeStep SetupTypeStep;
	public InstallerFolderDestinationStep FolderDestinationStep;
	public InstallerRepositoryDestinationStep PatchMngRepositoryStep;
	public InstallerStartMenuFolderStep StartMenuProgramStep;
	public InstallerLicenseFileStep LicenseFileStep;
	
	
	public InstallerApi(){
		SetupTypeStep = new InstallerSetupTypeStep();
		FolderDestinationStep = new InstallerFolderDestinationStep();
		PatchMngRepositoryStep = new InstallerRepositoryDestinationStep();
		StartMenuProgramStep = new InstallerStartMenuFolderStep();
		LicenseFileStep = new InstallerLicenseFileStep();
	}

}
