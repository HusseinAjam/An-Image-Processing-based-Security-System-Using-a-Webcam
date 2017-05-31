firstDraft = imresize(image ,2);
position = [10 10];
datAndTime = datestr (datetime('now')) ;
final = insertText(firstDraft,position,datAndTime);
name = strcat(num2str(counter), '.jpg')
imwrite(final, name);

firstDraft = imresize(image ,2);
Text = datestr (datetime('now')) ;
tex = vision.TextInserter(Text);
tex.Color = [1.0 1.0 1.0];
tex.FontSize = 12;
tex.Location = [10 10];
finalDraft = step(tex, firstDraft);
name = strcat(num2str(counter), '.jpg')
imwrite(finalDraft, name);
imshow(InsertedImage);