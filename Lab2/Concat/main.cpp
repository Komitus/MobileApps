#include <iostream>
#include <fstream>
#include <string>

int main(){
    

   std::fstream inFile;
   std::fstream outputFile;

   inFile.open("prev",std::ios::in);  

   outputFile.open("output",std::ios::out); 

   outputFile<<"<resources>"<<std::endl<<
    "<string name="<<'"'<<"app_name"<<'"'<<"Hangman</string>"<<std::endl
    <<"string-array name="<<'"'<<"word-bank"<<'"'<<">";
   if (inFile.is_open()){  
      
      std::string tp;
      while(getline(inFile, tp)){ 
        
        if(outputFile.is_open()) 
        {
            outputFile<<"<item>"<<tp<<"</item>"<<std::endl;
        }
      }
      
      outputFile<<"</string-array>"<<std::endl<<"</resources>"<<std::endl;
      inFile.close();
      outputFile.close(); 
      
   }
}
