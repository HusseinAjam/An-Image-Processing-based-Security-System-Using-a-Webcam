function Upload()
 d = dir('*.jpg');
 global userid;
 global conn;
 if (length(d) >= 10 )
    stry = ['java -jar Upload64.jar ',num2str(userid)];
   [status,cmdout] = dos(stry)
  query = sprintf('update Alarm_Notifications set notifications = 1 where user_id =''%s''', num2str(userid));       
  curs = exec(conn,query);
  curs = fetch(curs);
   pause(4);
   
   if (cmdout == 1)
   delete('*.jpg') ;
   end
 end
end 