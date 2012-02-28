//
//  ChannelParser.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ChannelParser : NSObject<NSXMLParserDelegate>
{
    @private
    NSXMLParser *xmlParser;
    NSString *currentElementName;
    NSString *_currentChannelId;
    NSMutableString *_currentChannelDesc;
    NSMutableArray *channels;
    
}

@property (retain, nonatomic) NSString *currentChannelId;
@property (retain, nonatomic) NSMutableString *currentChannelDesc;

- (NSMutableArray*) start;

@end
