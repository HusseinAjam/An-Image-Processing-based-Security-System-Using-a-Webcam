function varargout = evaluate(varargin)
% EVALUATE MATLAB code for evaluate.fig
%      EVALUATE, by itself, creates a new EVALUATE or raises the existing
%      singleton*.
%
%      H = EVALUATE returns the handle to a new EVALUATE or the handle to
%      the existing singleton*.
%
%      EVALUATE('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in EVALUATE.M with the given input arguments.
%
%      EVALUATE('Property','Value',...) creates a new EVALUATE or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before evaluate_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to evaluate_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help evaluate

% Last Modified by GUIDE v2.5 01-Mar-2016 15:50:12

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @evaluate_OpeningFcn, ...
                   'gui_OutputFcn',  @evaluate_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before evaluate is made visible.
function evaluate_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to evaluate (see VARARGIN)

% create an axes that spans the whole gui
ah = axes('unit', 'normalized', 'position', [0 0 1 1]); 
% import the background image and show it on the axes
bg = imread('bg2.jpg'); imagesc(bg);
% prevent plotting over the background and turn the axis off
set(ah,'handlevisibility','off','visible','off')
% making sure the background is behind all the other uicontrols
uistack(ah, 'bottom');

global conn;
 
conn = database('db13432608','s13432608','ajam1994',...
                'Vendor','MySQL',...
                'Server','194.81.104.22');
global y;
global Fs;
[y,Fs] = audioread('alarm.mp3');

global soundChecker;
soundChecker = 0;
            
% Choose default command line output for evaluate
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);
 global universal;
 universal = 0;

% UIWAIT makes evaluate wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = evaluate_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in Start.
function Start_Callback(hObject, eventdata, handles)
% hObject    handle to Start (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

 dlgTitle    = 'Old Recordings';
 dlgQuestion = 'Delete previous recordings?';
 choice = questdlg(dlgQuestion,dlgTitle,'Yes','No.', 'Yes');
  if(choice == 'Yes')
  global conn; 
  global userid; 
  sqlquery = ['delete from Live where '...
            'user_id = ' num2str(userid)];
        
  curs = exec(conn,sqlquery);
  
  curs = fetch(curs); 
  clear curs;
  curs = exec(conn,'ALTER TABLE Live AUTO_INCREMENT = 1');
  curs = fetch(curs); 
 end
 
 set (handles.End,'UserData',0)
 vid=videoinput('winvideo',1);
 set(vid,'FramesPerTrigger',Inf);
 set(vid,'ReturnedColorspace','RGB');
 vid.FrameGrabInterval=1;
 %triggerconfig(vid, 'manual');
 vidRes = get(vid, 'VideoResolution');
 imWidth = vidRes(1);
 imHeight = vidRes(2);
 nBands = get(vid, 'NumberOfBands');
 hImage = image(zeros(imHeight, imWidth, nBands), 'parent', handles.axes1)
 preview(vid, hImage);
 objectCounter = 0;
 imageCounter = 0;
  while(get(handles.End,'UserData') == 0 )  
     
 
   data1= getsnapshot(vid);
   pause(0.2);
   data2= getsnapshot(vid);
   
   convertToGray1= rgb2gray(data1);
   convertToGray2= rgb2gray(data2);
   
   diff_im1=medfilt2(convertToGray1,[3,3]);
   diff_im2=medfilt2(convertToGray2,[3,3]);
   
   diff=abs(diff_im2-diff_im1);
   diffAfetrMed=medfilt2(diff,[3,3]);
   
    BW = im2bw(diffAfetrMed);
   
    %x = sum(BW(:));
    set (handles.tt,'String',sum(BW(:)));
    
     if (sum(BW(:)) >= 5)     
     objectCounter = objectCounter +1;
     if (objectCounter >= 5 && imageCounter <= 10)
        imageCounter = imageCounter +1;
        setImage(data2, imageCounter);
     if (imageCounter == 10)
        clear objectCounter;
        clear imageCounter;
        objectCounter = 0;
        imageCounter = 0;
     end 
     end 
    end
 
    clear data1;
    clear data2;
    clear convertToGray1;
    clear convertToGray2;
    clear diff_im1;
    clear diff_im2;
    clear diff;
    clear diffAfetrMed;
    clear BW;
    alarmcheck();
    pause(0.2);
 end
     stop(vid);
     clear vid;


 


% --- Executes on button press in End.
function End_Callback(hObject, eventdata, handles)
% hObject    handle to End (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
prompt = {'Please enter your password'};
dlg_title = 'Login';
num_lines = 1;
answer = inputdlg(prompt,dlg_title,num_lines);
global userpass;
if(answer{1} == userpass)
 set(handles.End,'UserData',1) ;
else 
    msgbox('Wrong password', 'Error','error');
end


function alarmcheck()

global conn; 
global soundChecker;
global y;
global Fs;
global userid; 
 
sqlquery2 = ['select alarm from Alarm_Notifications where '...
            'user_id = ' num2str(userid)];
        
curs = exec(conn,sqlquery2);
curs = fetch(curs);
x = curs.Data;
resault = x{1};
if (resault == 1 && soundChecker == 0)
sound(y,Fs);
soundChecker = 1;
end
if (resault == 0 && soundChecker == 1)
clear sound;
soundChecker = 0;
end


% --- Executes on button press in pushbutton5.
function pushbutton5_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton5 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes during object creation, after setting all properties.
function axes3_CreateFcn(hObject, eventdata, handles)
% hObject    handle to axes3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

axes(hObject);
imshow('logo.png');
