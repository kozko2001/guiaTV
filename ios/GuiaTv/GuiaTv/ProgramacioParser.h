//
//  ProgramacioParser.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 24/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class ProgramacioModel;

@interface ProgramacioParser : NSObject<NSXMLParserDelegate>
{
@private
    NSXMLParser *xmlParser;
    NSString *currentElementName;
    NSString *currentTitle;
    NSString *currentDesc;
    NSString *currentCategoria;
    NSString *currentImagen;
    double currentStart;
    double currentEnd;

    NSMutableString *text;
    NSMutableArray *programas;
}

- (ProgramacioModel*)parse: (NSString*) channelId;

@end
