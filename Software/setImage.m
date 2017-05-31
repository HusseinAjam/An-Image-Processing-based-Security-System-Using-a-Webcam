function  setImage(image, counter)

firstDraft = imresize(image ,0.25);
Text = datestr (datetime('now')) ;
tex = vision.TextInserter(Text);
tex.Color = [248 248 255];
tex.FontSize = 13;
tex.Location = [10 10];
finalDraft = step(tex, firstDraft);
name = strcat(num2str(counter), '.jpg')
imwrite(finalDraft, name);
if(counter >= 10)
Upload();
end
end