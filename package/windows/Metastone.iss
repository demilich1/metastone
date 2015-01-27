;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Metastone with application files
[Setup]
AppId={{fxApplication}}
AppName=Metastone
AppVersion=0.9.0
AppVerName=Metastone
AppPublisher=demilich
AppComments=MetaStone
AppCopyright=Copyright (C) 2015
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\Metastone
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=demilich
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Metastone_Installer
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=Metastone\Metastone.ico
UninstallDisplayIcon={app}\Metastone.ico
UninstallDisplayName=Metastone
WizardImageStretch=No
WizardSmallImageFile=Metastone-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Metastone\Metastone.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Metastone\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Metastone"; Filename: "{app}\Metastone.exe"; IconFilename: "{app}\Metastone.ico"; Check: returnTrue()
Name: "{commondesktop}\Metastone"; Filename: "{app}\Metastone.exe";  IconFilename: "{app}\Metastone.ico"; Check: returnFalse()

[Run]
Filename: "{app}\Metastone.exe"; Description: "{cm:LaunchProgram,Metastone}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\Metastone.exe"; Parameters: "-install -svcName ""Metastone"" -svcDesc ""Metastone"" -mainExe ""Metastone.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\Metastone.exe "; Parameters: "-uninstall -svcName Metastone -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
