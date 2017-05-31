function varargout = login(varargin)
% LOGIN MATLAB code for login.fig
%      LOGIN, by itself, creates a new LOGIN or raises the existing
%      singleton*.
%
%      H = LOGIN returns the handle to a new LOGIN or the handle to
%      the existing singleton*.
%
%      LOGIN('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in LOGIN.M with the given input arguments.
%
%      LOGIN('Property','Value',...) creates a new LOGIN or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before login_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to login_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help login

% Last Modified by GUIDE v2.5 29-Feb-2016 14:14:08

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @login_OpeningFcn, ...
                   'gui_OutputFcn',  @login_OutputFcn, ...
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


% --- Executes just before login is made visible.
function login_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to login (see VARARGIN)

% create an axes that spans the whole gui
ah = axes('unit', 'normalized', 'position', [0 0 1 1]); 
% import the background image and show it on the axes
bg = imread('bg.jpg'); imagesc(bg);
% prevent plotting over the background and turn the axis off
set(ah,'handlevisibility','off','visible','off')
% making sure the background is behind all the other uicontrols
uistack(ah, 'bottom');

global userlogin;
userlogin =  0;

global userpass;
userpass =  0;

% Choose default command line output for login
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes login wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = login_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function user_Callback(hObject, eventdata, handles)
% hObject    handle to user (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of user as text
%        str2double(get(hObject,'String')) returns contents of user as a double


% --- Executes during object creation, after setting all properties.
function user_CreateFcn(hObject, eventdata, handles)
% hObject    handle to user (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function pass_Callback(hObject, eventdata, handles)
% hObject    handle to pass (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of pass as text
%        str2double(get(hObject,'String')) returns contents of pass as a double


% --- Executes during object creation, after setting all properties.
function pass_CreateFcn(hObject, eventdata, handles)
% hObject    handle to pass (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in login.
function login_Callback(~, eventdata, handles)
% hObject    handle to login (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
username = get(handles.user,'string');
password = get(handles.pass,'string');

conn = database('db13432608','s13432608','ajam1994',...
                'Vendor','MySQL',...
                'Server','194.81.104.22');

query = ['select 11 from Faculty_Members where member_id = ' password];          
curs = exec(conn,query);
curs = fetch(curs);
x = curs.Data;
resault = x{1};

global userlogin;
userlogin =  username;

global userpass;
userpass =  password;

if (resault == 11)
waitbar(1,'Loading...');
close;
evaluate;
else 
 msgbox('Wrong username or password', 'Error','error'); 
end 



% --- Executes during object creation, after setting all properties.
function username_CreateFcn(hObject, eventdata, handles)
% hObject    handle to username (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called


% --- Executes during object creation, after setting all properties.
function axes1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to axes1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: place code in OpeningFcn to populate axes1
axes(hObject);
imshow('logo.png');
